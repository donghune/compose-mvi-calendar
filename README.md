## 소개
이 프로젝트를 진행하는 인원의 조건은 아래와 같습니다.
1. 협업의 경험이 적거나, 전무한 상태의 개발자. 
2. PR과정에 대한 경험이 없는 개발자.
3. MVVM 또는 MVI를 접해보고 싶은 개발자.
4. 회사의 인원 외, 타 인원과의 협업 경험이 필요한 자.

위 조건을 가진 개발자로 이루어진 팀이, PO(PPIZIL)의 요구사항에 맞게 개발을 수행한 후,
상호간의 PR 및 리뷰를 통해 기본 능력을 함양한다.


## 프로젝트 진행 기술 조건
1. Android + Kotlin ( Required )
2. MVVM Or MVI  ( MvI Optional )
3. Hilt Or CleanArchitectur ( Optional )
4. Compose or Xml ( Compose Optional)


## 과제 내용 
### 1. 캘린더 메인
해당월의 Year, Month, Days를 나타내는 캘린더를 메인 페이지로 한다.
이때 이 페이지에서는 좌,우로 스와이프(플링)가능한 인터렉션이 존재한다.
[기본 이미지]
<img width="330" alt="image" src="https://github.com/user-attachments/assets/80403c63-f7d5-48d0-b2a3-c84b6ca3ab9a" />

[좌우 인터렉션에 대한 동영상]
https://github.com/user-attachments/assets/636c418c-8c1f-4de8-a842-37faccffc20f

1. Year. Month 영역은 yyyy.MM 의 형태로 표현되어야 한다.
2. Year. Month 영역은 좌,우 클릭을 통해 이전과 다음달로 이동 가능해야하며, 12월 이후 또는 1월 이전에는 Year이 그에 맞게 증감 되어야한다.
3. DayOfWeek 영역은 월요일을 시작으로 일요일까지 순차적으로 나타낸다.
4. DayCell 영역은 1 ~ 마지막일 까지 정확하게 표현해야한다.
5. 시작하는 요일 이전의 날짜는 공백으로 표현한다. ( Ex: 수요일 시작이라면 월, 화는 공백으로 표시 후 수요일부터 1일 표현 )
6. 마지막 일자 가 월~토 의 요일이라면, 나머지 일요일까지는 공백으로 노출한다.
7. 캘린더의 특정 일을 클릭하게 되면 해당 일의 상세 정보를 입력할 수 있는 페이지로 이동한다.
8. 선택된 날짜는 Border처리, Border된 날짜를 다시한번 클릭했을 때 상세 페이지로 이동한다.
<img width="336" alt="스크린샷 2025-01-12 오후 6 27 07" src="https://github.com/user-attachments/assets/1e6365ee-f55d-4c1f-902b-112bda42ee50" />

### 2. 캘린더 일자 상세 페이지
1. 하루를 한줄로 요약할 수있는 필드, 자유롭게 서술할 수있는 필드, 이모지를 통해 하루를 나타낼 수있는 필드 총 3가지의 필드가 존재한다.
2. 이모지를 클릭하게 되면 바텀 시트를 통해 ( Smile, Angry, Sad ) 3가지 이모지를 노출해 선택할 수 있게 한다.
3. 해당 필드의 각 입력값이 페이지 종료 시, Local IO ( Shared or DataStore )을 통해 저장된다.
<img width="330" alt="스크린샷 2025-01-12 오후 6 17 13" src="https://github.com/user-attachments/assets/5fb1d1ab-ecea-4fc7-8dfc-16ce9cdefa3d" />
4. 각 텍스트 입력 필드는 Trailing Icon으로 Clear역할을 하는 기능을 추가한다.  ( X 버튼 클릭 시 입력값 초기화 )

### 3. 상세 입력 후 캘린더 페이지로 돌아왔을 때
1. 해당 입력된 이모지 종류에 따라 Calendar의 각 일자에 대표 이모지가 표현된다.
2. 입력되지 않은 날은 ?가 입력되고, 입력된 날은 해당 이모지가 색상별로 표현된다.
<img width="339" alt="스크린샷 2025-01-12 오후 6 25 03" src="https://github.com/user-attachments/assets/79d9997c-5ac4-4219-9b7e-54d8cb97547f" /> 


### 4. 주요 요구사항
1. 일의 상세 페이지에서 입력한 값은 앱을 재시작 해도 유지되어야함.
2. 달력을 스와이프 할떄 좌우 Rendering이 매끄러워아함. ( 가급적 빈 화면, 버벅임등 존재하지 않도록 )  이 부분에 유의하며 개발할 것.
3. 달력이 변경될 때 상세 정보가 입력된 일은 이모지를 동기화 시킬 것
4. 상세 페이지에서는 반드시 페이지 이탈과 동시에 저장할 것. ( SharedPreference or Data Store 사용 )


## 진행 방식

- 리뷰이
1. 각 개발자의 NickName에 따라 Branch를 생성한다.
ex) develop -> develop/ppizil/
2. 해당 개발자의 Branch내 부에서 MVVM,MVI 2개의 브랜치를 생성한다.
ex) develop -> develop/ppizil/mvvm , develop/ppizil/mvi
3. 각 패턴에 따라 LocalBranch에서 Feature브랜치를 분기한다.
4. develop/ppizil/mvvm/feature/{FeatureName}
5. Feature Branch의 개발 완료 시, 분기되었던 Branch로 PR을 작성한다.
   - 이때 PR은 본인이 세분화 할수 있는 기준에 따라 디펜던시를 최소화 한, PR을 생성한다.
6. Github Issue를 생성해 각 IsseuNumber를 feature브랜치의 Prefix로 삼는다.
7. Prefix를 CommitMessage에 기입 후, 해당 Feature의 내용을 요약하는 한줄 CommitMessage를 남긴다.
   -> [#1] 캘린더 년월 라인 개발 / [#2] 해당 월의 DayList 계산 로직 개발  등
8. PR은 미리 만들어진 템플릿에 맞게 내용을 작성한다.
9. PR 작성 시 , Asignment 및 Reveiwer를 지정한다 . 

- 리뷰어
1. 각 PR을 3일 이내 확인 한다.
2. 리뷰에 대한 코멘트는 아래 레벨링 규칙에 따라  작성한다.
3. 가장 마지막 Approve를 진행하는 사람이 Label을 Wait_Meger로 변경한다.
4. ( 소규모 인원이기 떄문에 모든 이의 Approve를 받았을 때 전환 가능 ) 
```
코멘트 규칙 
- LV5 : 코멘트 작성자 조차 불확실 하지만 찝찝한 정도의 의미  ( 상호간의 합의 불필요 )
- LV4 : 고려는 해보았으면 하도록 주지 시키는 의미 ( 상호간의 합의 불필요 )
- LV3 :  웬만하면 고려하고, 반영해줬으면 좋겠다는 의미 ( 상호간의 합의 불필요 )
- LV2 : 적극적으로 고려해줬으면 좋겠다. 필요 시 회의 가능. ( 상호간의 합의 필요 )
- LV1 : 반드시 합의 후 수정이 필요허다. ( 상호간의 합의 필요 )
```

### 고려하지 않아도 되는 부분
1. 컴포넌트
2. 색상
3. 요구사항에 명시되어 있지 않은 사항. ( 애매한 부분은 질문 주세요 . )

### 참고 리소스 
[이모지.zip](https://github.com/user-attachments/files/18388763/default.zip)




### 진행 순서 ( ********* )
1. MVVM + Xml
2. MVVM + Compose
3. MVI + Compose 
각 세가지의 방식으로 동일한 과제를 수행하고자 합니다. 
기본적으로 만들어져야 하는 브랜치는 3가지 입니다.
1. develop/ppizil/mvvm/xml
   - feature/...
2. develop/ppizil/mvvm/compose
   - feature/...
3. develop/ppizil/mvi/compose
   - feature/...

### 과제 진행 기간 
2025.01.13 ~ 2025.01.26 ( 약 2주 ) 


# compose-mvi-calendar
