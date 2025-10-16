# 요구사항

- Spring Boot 프로젝트를 작성한다.
    - Spring Boot 최신 릴리즈 버전, Java 17 이상으로 작성한다.
    - 제공하는 상수도 요금표와 회원 정보를 메모리에 로드하고, 로그인한 사용자가 상수도 요금을 계산할 수 있는 프로그램을 작성한다.
    - 프로젝트는 CLI 를 통해서 input 을 받고 적절한 응답 값을 반환한다.
        - Spring Shell 방식을 추천
            - [Spring Shell](https://nhnacademy.dooray.com/share/pages/ABFgz8KgRv62A85x9KClUw/3885036920804056648)
        - Spring Shell 적용이 어렵다면, 다른 라이브러리를 사용하거나 직접 구현해도 된다. 다만 비즈니스 로직과 input 처리 부분의 코드는 분리하도록 한다.
            - ex) ApplicationRunner 에 Scanner 적용