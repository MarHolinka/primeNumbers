package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.out.println("Absolute path to excell: ");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine().trim();
        excellRead(filePath);
    }

    private static void excellRead(String filePath){
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            //get first sheet
            Sheet sheet = workbook.getSheetAt(0);

            FormulaEvaluator eval = workbook.getCreationHelper().createFormulaEvaluator();

            for (Row row : sheet) {

                Cell cell = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (cell == null) continue;

                Long number = null;

                //get numbers written as numbers
                if (cell.getCellType() == CellType.NUMERIC) {
                    number = (long) cell.getNumericCellValue();
                    //check for text inserted numbers
                } else if (cell.getCellType() == CellType.STRING) {
                    String s = cell.getStringCellValue().trim();
                    if (s.matches("\\d+")) {
                        number = Long.parseLong(s);
                    }
                    //check for SUM(a,a) if it results in number
                } else if (cell.getCellType() == CellType.FORMULA) {
                    CellValue cellValue = eval.evaluate(cell);
                    if (cellValue != null && cellValue.getCellType() == CellType.NUMERIC) {
                        number = (long) cellValue.getNumberValue();
                    }
                }
                //check for prime numbers and log
                if (number != null && isPrime(number)) {
                    logger.info(String.valueOf(number));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //small method to check for prime numbers
    static boolean isPrime(long n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}