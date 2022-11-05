package ru.matveyakulov.markoservcomeback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.matveyakulov.markoservcomeback.domain.Holiday;
import ru.matveyakulov.markoservcomeback.parser.HolidayParser;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestService {

    private final CloudService cloudService;

    @Autowired
    public RestService(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    public List<String> getAllByMouthAndDay(String mouth, Integer day) {
        cloudService.getFile();
        List<String> values = ExcelService.read("holidaysFromCloud.xlsx")
                .stream()
                .filter(h -> h.getDay().equals(day))
                .filter(h -> h.getMonth().equals(mouth))
                .map(Holiday::getValue)
                .collect(Collectors.toList());
        if (values.isEmpty()) {
            values =  addByMouthAndDay(mouth, day);
        }
        cloudService.getSoundAnswer(values.get(0));
        return values;
    }

    public List<String> addByMouthAndDay(String mouth, Integer day) {
        List<Holiday> holidays = HolidayParser.getHolidaysByMouthAndDay(mouth, day);
        String path = ExcelService.write(holidays);
        cloudService.uploadFile(path);
        return holidays.stream().map(Holiday::getValue).collect(Collectors.toList());
    }
}
