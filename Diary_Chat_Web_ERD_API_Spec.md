# Diary Chat Web - ERD 및 API 명세서

## 1. ERD 설계

``` text
users
- id PK
- user_id
- email
- password
- nickname
- phone_number
- profile_image_url
- created_at
- updated_at
- deleted_at

diaries
- id PK
- user_id FK → users.id
- title
- content
- mood
- weather
- diary_date
- is_private
- created_at
- updated_at

diary_images
- id PK
- diary_id FK → diaries.id
- image_url
- created_at

tags
- id PK
- name

diary_tags
- diary_id FK → diaries.id
- tag_id FK → tags.id

chat_sessions
- id PK
- user_id FK → users.id
- diary_id FK → diaries.id (NULL 가능)
- title
- created_at
- updated_at

chat_messages
- id PK
- session_id FK → chat_sessions.id
- sender
- content
- created_at
```

## 2. 테이블 관계

``` text
User 1 : N Diary
Diary 1 : N DiaryImage
Diary N : M Tag
User 1 : N ChatSession
ChatSession 1 : N ChatMessage
Diary 1 : N ChatSession
```

## 3. 테이블 상세

### users

  컬럼                타입       설명
  ------------------- ---------- -------------------
  id                  BIGINT     사용자 ID
  user_id             VARCHAR    로그인 아이디
  email               VARCHAR    로그인 이메일
  password            VARCHAR    암호화된 비밀번호
  nickname            VARCHAR    닉네임
  phone_number        VARCHAR    휴대폰 번호
  profile_image_url   TEXT       프로필 이미지
  created_at          DATETIME   생성일
  updated_at          DATETIME   수정일
  deleted_at          DATETIME   탈퇴일

### diaries

  컬럼         타입       설명
  ------------ ---------- -------------
  id           BIGINT     일기 ID
  user_id      BIGINT     작성자
  title        VARCHAR    제목
  content      TEXT       본문
  mood         VARCHAR    감정
  weather      VARCHAR    날씨
  diary_date   DATE       일기 날짜
  is_private   BOOLEAN    비공개 여부
  created_at   DATETIME   생성일
  updated_at   DATETIME   수정일

### chat_messages

  컬럼         타입             설명
  ------------ ---------------- --------------
  id           BIGINT           메시지 ID
  session_id   BIGINT           채팅 세션 ID
  sender       ENUM(USER, AI)   발신자
  content      TEXT             메시지 내용
  created_at   DATETIME         생성일

------------------------------------------------------------------------

# API 명세

## Auth API

### 회원가입

**POST** `/api/auth/signup`

#### Request

``` json
{
  "userId": "seungmin",
  "email": "test@example.com",
  "password": "12345678",
  "nickname": "승민",
  "phoneNumber": "010-1234-5678"
}
```

#### Response

``` json
{
  "id": 1,
  "userId": "seungmin",
  "email": "test@example.com",
  "nickname": "승민",
  "phoneNumber": "010-1234-5678"
}
```

------------------------------------------------------------------------

### 로그인

**POST** `/api/auth/login`

#### Request

``` json
{
  "email": "test@example.com",
  "password": "12345678"
}
```

#### Response

``` json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token"
}
```

------------------------------------------------------------------------

## User API

### 내 정보 조회

**GET** `/api/users/me`

#### Response

``` json
{
  "id": 1,
  "email": "test@example.com",
  "nickname": "승민",
  "profileImageUrl": null
}
```

------------------------------------------------------------------------

### 내 정보 수정

**PATCH** `/api/users/me`

#### Request

``` json
{
  "nickname": "새닉네임",
  "profileImageUrl": "https://example.com/profile.png"
}
```

------------------------------------------------------------------------

## Diary API

### 일기 작성

**POST** `/api/diaries`

#### Request

``` json
{
  "title": "오늘의 일기",
  "content": "오늘은 기분이 좋았다.",
  "mood": "HAPPY",
  "weather": "SUNNY",
  "diaryDate": "2026-07-01",
  "isPrivate": true,
  "tags": ["행복", "운동"]
}
```

#### Response

``` json
{
  "diaryId": 1
}
```

------------------------------------------------------------------------

### 일기 목록 조회

**GET** `/api/diaries?page=0&size=10`

#### Response

``` json
{
  "content": [
    {
      "id": 1,
      "title": "오늘의 일기",
      "mood": "HAPPY",
      "diaryDate": "2026-07-01",
      "createdAt": "2026-07-01T12:00:00"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1
}
```

------------------------------------------------------------------------

### 일기 상세 조회

**GET** `/api/diaries/{diaryId}`

------------------------------------------------------------------------

### 일기 수정

**PATCH** `/api/diaries/{diaryId}`

------------------------------------------------------------------------

### 일기 삭제

**DELETE** `/api/diaries/{diaryId}`

------------------------------------------------------------------------

## Chat API

### 채팅 세션 생성

**POST** `/api/chat-sessions`

#### Request

``` json
{
  "diaryId": 1
}
```

#### Response

``` json
{
  "sessionId": 1,
  "title": "오늘의 일기 회고"
}
```

------------------------------------------------------------------------

### 채팅 메시지 전송

**POST** `/api/chat-sessions/{sessionId}/messages`

#### Request

``` json
{
  "content": "오늘 일기를 분석해줘."
}
```

#### Response

``` json
{
  "userMessage": {
    "sender": "USER",
    "content": "오늘 일기를 분석해줘."
  },
  "aiMessage": {
    "sender": "AI",
    "content": "오늘 하루는 긍정적인 감정이 많이 드러났습니다."
  }
}
```

------------------------------------------------------------------------

### 채팅 내역 조회

**GET** `/api/chat-sessions/{sessionId}/messages`

------------------------------------------------------------------------

## Enum

### Mood

-   HAPPY
-   SAD
-   ANGRY
-   CALM
-   ANXIOUS
-   TIRED
-   EXCITED

### Weather

-   SUNNY
-   CLOUDY
-   RAINY
-   SNOWY
-   WINDY

### Sender

-   USER
-   AI
