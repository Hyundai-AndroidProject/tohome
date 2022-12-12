package com.example.hyundai_to_home.listner

/**
 * 클래스 설명 : 예약 리스트 클릭 이벤트를 위한 인터페이스
 *
 * @author  박서은
 * 박서은 - 예약리스트 선택시 클릭 이벤트를 위한 추상함수 생성
 */
interface OnClickListener {
    fun OnReservationClick(storeId : Int, reservationId : Int)
}