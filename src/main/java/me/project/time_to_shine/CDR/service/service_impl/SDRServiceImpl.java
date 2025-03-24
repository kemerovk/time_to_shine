package me.project.time_to_shine.CDR.service.service_impl;

import me.project.time_to_shine.CDR.exception.custom.ReportNotFoundException;
import me.project.time_to_shine.CDR.model.CallDirection;
import me.project.time_to_shine.CDR.model.Client;
import me.project.time_to_shine.CDR.model.ClientInterval;
import me.project.time_to_shine.CDR.model.ReportSDR;
import me.project.time_to_shine.CDR.repo.ClientRepository;
import me.project.time_to_shine.CDR.repo.SDRRepository;
import me.project.time_to_shine.CDR.service.SDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

/**
 * Реализация сервиса для работы с отчетами о звонках (CDR — Call Detail Records).
 * Предоставляет методы для создания, получения, удаления отчетов, а также генерации случайных отчетов.
 */
@Service
public class SDRServiceImpl implements SDRService {

    @Autowired
    private SDRRepository sdrRepository;

    @Autowired
    private ClientRepository clientRepo;

    /**
     * Возвращает список всех отчетов, хранящихся в базе данных.
     *
     * @return список всех отчетов.
     */
    @Override
    public List<ReportSDR> getReports() {
        return sdrRepository.findAll();
    }

    /**
     * Возвращает отчет по его идентификатору.
     *
     * @param id идентификатор отчета.
     * @return найденный отчет.
     * @throws ReportNotFoundException если отчет с указанным ID не найден.
     */
    @Override
    public ReportSDR getReport(int id) {
        Optional<ReportSDR> opt = sdrRepository.findById(id);
        if (opt.isEmpty()) {
            throw new ReportNotFoundException("не получилось найти отчет с id = " + id);
        }
        return opt.get();
    }

    /**
     * Сохраняет новый отчет в базе данных.
     *
     * @param report объект отчета для сохранения.
     * @return сохраненный отчет.
     */
    @Override
    public ReportSDR addReport(ReportSDR report) {
        return sdrRepository.save(report);
    }

    /**
     * Генерирует случайные отчеты о звонках. Если в базе данных уже есть отчеты, метод завершает работу.
     * В противном случае создается случайное количество отчетов (от 1 до 1000) с использованием случайных данных.
     *
     * @return количество сгенерированных отчетов.
     */
    @Override
    public int generateReports() {

        if (!sdrRepository.findAll().isEmpty()) return 0;

        Random random = new Random();
        int numOfReports = random.nextInt(1000) + 1;

        List<ReportSDR> reports = new ArrayList<>();
        List<Client> clients = clientRepo.findAll();

        int firstId;
        int secondId;

        LocalDate startDate = LocalDate.of(2025, Month.JANUARY, 1);

        for (int i = 0; i < numOfReports; i++) {
            do {
                firstId = random.nextInt(clients.size());
                secondId = random.nextInt(clients.size());
            } while (firstId == secondId);

            ReportSDR report = new ReportSDR();
            Client firstClient = clients.get(firstId);
            Client secondClient = clients.get(secondId);

            report.setCallDirection(random.nextDouble() <= 0.5 ? CallDirection.INCOMING : CallDirection.OUTGOING);
            report.setCallerNumber(firstClient.getNumber());
            report.setReceiveNumber(secondClient.getNumber());

            LocalDateTime firstDate = LocalDateTime.of(
                    startDate.plusDays(random.nextInt(365)),
                    LocalTime.of(random.nextInt(24),
                            random.nextInt(60),
                            random.nextInt(60)));

            Duration duration = Duration.ofHours(random.nextInt(1)).
                    plusMinutes(random.nextInt(10)).
                    plusSeconds(random.nextInt(60));

            LocalDateTime secondDate = firstDate.plus(duration);

            if (!(isDateValid(firstDate, secondDate, firstClient) &&
                    isDateValid(firstDate, secondDate, secondClient))) continue;
            report.setBeginningDate(firstDate);
            report.setEndingDate(firstDate.plus(duration));

            firstClient.getIntervals().add(new ClientInterval(CallDirection.OUTGOING, firstDate, secondDate));
            secondClient.getIntervals().add(new ClientInterval(CallDirection.INCOMING, firstDate, secondDate));

            reports.add(report);
        }

        reports.sort(Comparator.comparing(ReportSDR::getEndingDate));
        sdrRepository.saveAll(reports);
        return reports.size();
    }

    /**
     * Проверяет, что интервал времени нового звонка не пересекается с уже существующими интервалами звонков для указанного абонента.
     *
     * @param beginToCheck время начала нового звонка.
     * @param endToCheck   время окончания нового звонка.
     * @param client       абонент, для которого проверяется интервал.
     * @return true, если интервал валиден (нет пересечений), иначе false.
     */
    private boolean isDateValid(LocalDateTime beginToCheck, LocalDateTime endToCheck, Client client) {
        long count = client.getIntervals().
                stream().
                filter(x -> beginToCheck.isAfter(x.getEndTime()) || endToCheck.isBefore(x.getStartTime())).
                count();

        return client.getIntervals().size() == count;
    }

    /**
     * Удаляет отчет по его идентификатору.
     *
     * @param id идентификатор отчета.
     */
    @Override
    public void deleteReport(int id) {
        ReportSDR report = sdrRepository.findById(id).get();
        sdrRepository.delete(report);
    }

    /**
     * Возвращает список интервалов звонков для указанного абонента.
     *
     * @param clientId идентификатор абонента.
     * @return список интервалов звонков.
     */
    public List<ClientInterval> getListOfClientIntervalsByClientId(int clientId) {
        Client client = clientRepo.findById(clientId).get();
        List<ClientInterval> clientIntervals = new ArrayList<>();
        List<ReportSDR> reports = sdrRepository.findAll();

        for (ReportSDR report : reports) {
            if (report.getCallerNumber().equals(client.getNumber()) || report.getReceiveNumber().equals(client.getNumber())) {
                clientIntervals.add(report.getCallerNumber().equals(client.getNumber()) ?
                        new ClientInterval(CallDirection.OUTGOING, report.getBeginningDate(), report.getEndingDate()) :
                        new ClientInterval(CallDirection.INCOMING, report.getBeginningDate(), report.getEndingDate()));
            }
        }

        return clientIntervals;
    }
}