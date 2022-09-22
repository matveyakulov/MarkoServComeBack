package ru.matveyakulov.markoservcomeback.service;

import org.springframework.stereotype.Service;
import ru.matveyakulov.markoservcomeback.domain.Holiday;
import ru.matveyakulov.markoservcomeback.parser.HolidayParser;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestService {
    public List<String> getAllByMouthAndDay(String mouth, Integer day) {
        CloudService.getFile();
        List<String> values = ExcelService.read("holidaysFromCloud.xlsx")
                .stream()
                .filter(h -> h.getDay().equals(day))
                .filter(h -> h.getMonth().equals(mouth))
                .map(Holiday::getValue)
                .collect(Collectors.toList());
        if (values.size() > 0) {
            return values;
        }
        return addByMouthAndDay(mouth, day);
    }

    public List<String> addByMouthAndDay(String mouth, Integer day) {
        List<Holiday> holidays = HolidayParser.getHolidaysByMouthAndDay(mouth, day);
        String path = ExcelService.write(holidays);
        CloudService.uploadFile(path);
        return holidays.stream().map(Holiday::getValue).collect(Collectors.toList());
    }
}
