package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import ru.otus.spring.model.TestUnit;
import ru.otus.spring.mapper.TestUnitMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class CsvTestDao implements TestDao {
    private String fileName;

    public CsvTestDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<TestUnit> getTestUnits() {
        List<TestUnit> testUnits = null;
        try (InputStream in = getClass().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(in));
             CSVReader reader = new CSVReader(br)) {
            List<String[]> r = reader.readAll();
            testUnits = r.stream().map(x -> TestUnitMapper.transform(x)).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return testUnits;
    }

}
