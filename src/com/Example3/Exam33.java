package com.Example3;

public class Exam33 {
    public static void main(String[] args) {
        // 1.int형 변수 gender 를 선언하고, 0 또는 1을 대입한다 (어떤 것이라도 상관없음)
        int gender = 0;
        // 또한, int 형 변수 age 를 선언하고, 적당한 숫자를 대입한다.
        int age = 27;
        // 2.화면에 “안녕하세요” 를 표시한다
        System.out.println("안녕하세요");
        // 3.만약 변수 gender 가 0이면 “나는 남자입니다", 그렇지 않으면 “나는 여자입니다" 를 표시한다
        if (gender == 0) {
            System.out.println("나는 남자입니다");
        } else {
            System.out.println("나는 여자입니다");
        }
        // 4.만약 변수 gender 가 남자이면 age 변수의 값을 표시하고, 뒤에 “살입니다" 를 붙여서 표시한다
        if (gender == 0) {
            System.out.println(age + "살입니다");
        }
        // 5.마지막에 “잘 부탁합니다" 를 표시한다
        System.out.println("잘 부탁합니다");
    }
}
