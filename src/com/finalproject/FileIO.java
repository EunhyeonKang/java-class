package com.finalproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class FileIO implements CRUDInterface {
    String[] memberHeader;
    String[] bookHeader;
    Scanner sc;

    public FileIO() {
        memberHeader = new String[] {"아이디", "이름", "주소", "전화번호", "구분"};
        bookHeader = new String[] {"도서번호", "제목", "저자", "출판사", "출판년도", "대출여부", "대출 후 남은 도수 개수"};
        sc = new Scanner(System.in);
    }

    // 가입
    public void registerByFile(String fileName) {
        String filePath = "C:\\Users\\DA\\eclipse-workspace\\data\\" + fileName + ".csv";
        try {
            Scanner sc = new Scanner(System.in);
            BufferedWriter writer;
            CSVWriter csvWriter;
            writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(filePath, true), "EUC-KR"));
            csvWriter = new CSVWriter(writer);

            // 회원 정보 입력
            String[] memberInfo = Input(fileName);

            csvWriter.writeNext(memberInfo);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 전체 조회
    @Override
    public void selectByFile(String fileName) {
        String filePath = "C:\\Users\\DA\\eclipse-workspace\\data\\" + fileName + ".csv";
        File file = new File(filePath);
        CSVReader reader = null;

        try {
            reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "EUC-KR"));
            String[] line;
            while ((line = reader.readNext()) != null) {
                for (String value : line) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CsvValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // 검색
    @Override
    public boolean selectOneMember(String loginId, String role) {
        String filePath = "C:\\Users\\DA\\eclipse-workspace\\data\\member.csv";
        try {
            // CSV 파일을 읽기 위한 CSVReader 객체 생성
            CSVReader reader =
                    new CSVReader(new InputStreamReader(new FileInputStream(filePath), "EUC-KR"));
            // 특정 행을 선택
            String[] row; // 첫 번째 행
            while ((row = reader.readNext()) != null) {
                // 현재 행의 첫 번째 값 출력
                if (loginId.equals(row[0]) && row[4].equals(role)) {
                    return true;
                }
            }
            // 이름 값 추출

            // CSVReader 객체 닫기
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    // 수정
    @Override
    public void updateByFile(String fileName) {
        String filePath = "C:\\Users\\DA\\eclipse-workspace\\data\\" + fileName + ".csv";
        Scanner sc = new Scanner(System.in);
        // row값이 -1
        int rowToUpdate = sc.nextInt() - 1;
        String[] newData = Input(fileName);

        File file = new File(filePath);

        try {
            CSVReader reader = null;
            // csv 파일을 읽음
            reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "EUC-KR"));
            List<String[]> lines = reader.readAll();
            reader.close();

            // 수정한 라인에 새로운 데이터로 수정
            lines.set(rowToUpdate, newData);

            // CSV파일을 업데이트
            CSVWriter writer =
                    new CSVWriter(new OutputStreamWriter(new FileOutputStream(filePath), "EUC-KR"));

            writer.writeAll(lines);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 삭제
    @Override
    public void deleteByFile(String fileName, String backupName) {
        String filePath = "C:\\Users\\DA\\eclipse-workspace\\data\\" + fileName + ".csv";
        String backupFilePath = "C:\\Users\\DA\\eclipse-workspace\\data\\" + backupName + ".csv";

        try {
            // CSV 파일을 읽어들임
            CSVReader reader =
                    new CSVReader(new InputStreamReader(new FileInputStream(filePath), "EUC-KR"));

            // csv파일의 모든 행열을 lines에 담기
            List<String[]> lines = reader.readAll();

            reader.close();
            Scanner sc = new Scanner(System.in);
            // 삭제할 행을 선택하고 삭제
            int rowToDelete = sc.nextInt(); // 삭제할 행의 인덱스
            lines.remove(rowToDelete);

            // CSV 파일을 업데이트

            CSVWriter writer =
                    new CSVWriter(new OutputStreamWriter(new FileOutputStream(filePath), "EUC-KR"));

            for (String[] line : lines) {
                writer.writeNext(line);
            }
            // 백업 파일 생성
            try (CSVWriter writer1 = new CSVWriter(
                    new OutputStreamWriter(new FileOutputStream(backupFilePath), "EUC-KR"))) {
                writer1.writeAll(lines); // 데이터를 백업 파일에 저장
            }
            writer.flush();
            writer.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    // 라인 입력 로직
    private String[] Input(String name) {
        String[] memberInfo;
        if (name.equals("member")) {
            memberInfo = new String[memberHeader.length + 1];
            for (int i = 0; i < memberHeader.length; i++) {
                UI.print(memberHeader[i]);
                memberInfo[i] = sc.next();
            }
        } else {
            memberInfo = new String[bookHeader.length + 1];
            for (int i = 0; i < bookHeader.length; i++) {
                UI.print(bookHeader[i]);
                memberInfo[i] = sc.next();
            }
        }

        return memberInfo;
    }

    @Override
    public void selectByFile(String fileName, String memberId) {
        String filePath = "C:\\Users\\DA\\eclipse-workspace\\data\\" + fileName + ".csv";
        File file = new File(filePath);
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "EUC-KR"));
            String[] line;
            // 대출이력조회 후 대출가능한 도서 조회
            // System.out.println(memberId);

            while ((line = reader.readNext()) != null) {
                for (String value : line) {
                    if (memberId.equals(value)) {
                        break;
                    } else {
                        System.out.print(value + "\t");
                    }

                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CsvValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void registerLoan(String memberId, String input, String fileName) {
        String filePath = "C:\\Users\\DA\\eclipse-workspace\\data\\" + fileName + ".csv";


        String filebook = "C:\\Users\\DA\\eclipse-workspace\\data\\book.csv";
        File file = new File(filebook);
        CSVReader reader = null;

        try {
            reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "EUC-KR"));
            String[] line;
            while ((line = reader.readNext()) != null) {
                for (String value : line) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CsvValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        try {
            Scanner sc = new Scanner(System.in);
            BufferedWriter writer;
            CSVWriter csvWriter;
            writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(filePath, true), "EUC-KR"));
            csvWriter = new CSVWriter(writer);
            // 현재 날짜 구하기
            LocalDate now = LocalDate.now();
            // 포맷 정의
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            // 포맷 적용
            String formatedNow = now.format(formatter);
            // 대출정보입력(대출한 아이디, 대출날짜, 도서아이디)


            // 14일 추가
            LocalDate after14Days = now.plusDays(14);
            // 포맷 정의
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            // 포맷 적용
            String formattedAfter14Days = after14Days.format(formatter2);


            String[] memberInfo = new String[] {memberId, formatedNow, formattedAfter14Days, input};

            csvWriter.writeNext(memberInfo);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
