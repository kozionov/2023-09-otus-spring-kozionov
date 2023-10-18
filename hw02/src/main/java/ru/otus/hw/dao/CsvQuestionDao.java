package ru.otus.hw.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        try (InputStream in = getClass().getResourceAsStream(fileNameProvider.getTestFileName());
             BufferedReader br = new BufferedReader(new InputStreamReader(in));
             CSVReader reader = new CSVReader(br)) {

            ColumnPositionMappingStrategy<QuestionDto> beanStrategy = new ColumnPositionMappingStrategy<>();
            beanStrategy.setType(QuestionDto.class);

            CsvToBean<QuestionDto> csvToBean = new CsvToBean<>();
            csvToBean.setCsvReader(reader);
            csvToBean.setMappingStrategy(beanStrategy);

            List<QuestionDto> questionDtos = csvToBean.parse();
            return questionDtos.stream()
                    .map(x -> x.toDomainObject())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new QuestionReadException("Ошибка чтения файла", e);
        }
    }
}
