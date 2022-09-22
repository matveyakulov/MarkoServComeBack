package ru.matveyakulov.markoservcomeback.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Holiday {

    private String month;

    private Integer day;

    private String value;
}
