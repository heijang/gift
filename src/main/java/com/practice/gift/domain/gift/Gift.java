package com.practice.gift.domain.gift;

import com.practice.gift.common.exception.IllegalStatusException;
import com.practice.gift.common.exception.InvalidParamException;
import com.practice.gift.common.util.TokenGenerator;
import com.practice.gift.domain.AbstractEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "gifts")
public class Gift extends AbstractEntity {

    private static final String GIFT_PREFIX = "gt_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String giftToken;
    private Long buyerUserId;
    private String orderToken;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PushType pushType;
    private String giftReceiverName;
    private String giftReceiverPhone;
    private String giftMessage;

    private String receiverName;
    private String receiverPhone;
    private String receiverZipcode;
    private String receiverAddress1;
    private String receiverAddress2;
    private String etcMessage;

    private ZonedDateTime paidAt;
    private ZonedDateTime pushedAt;
    private ZonedDateTime acceptedAt;
    private ZonedDateTime expiredAt;

    @Getter
    @AllArgsConstructor
    public enum Status {
        INIT("선물 주문 생성"),
        IN_PAYMENT("결제 중"),
        ORDER_COMPLETE("선물 링크 발송 완료"),
        PUSH_COMPLETE("선물 링크 발송 완료"),
        ACCEPT("선물 수락"),
        DELIVERY_PREPARE("상품준비"),
        IN_DELIVERY("배송중"),
        DELIVERY_COMPLETE("배송완료"),
        EXPIRATION("선물 수락 만료");

        private final String description;
    }

    @Getter
    @AllArgsConstructor
    public enum PushType {
        KAKAO("카카오톡"),
        LMS("문자");

        private final String description;
    }

    @Builder
    public Gift(Long buyerUserId, String orderToken, PushType pushType, String giftReceiverName, String giftReceiverPhone, String giftMessage) {
        if (buyerUserId == null) throw new InvalidParamException("Gift constructor buyerUserId is null");
        if (pushType == null) throw new InvalidParamException("Gift constructor pushType is null");
        if (StringUtils.isEmpty(giftReceiverName)) throw new InvalidParamException("Gift constructor giftReceiverName is empty");
        if (StringUtils.isEmpty(giftReceiverPhone)) throw new InvalidParamException("Gift constructor giftReceiverPhone is empty");
        if (StringUtils.isEmpty(giftMessage)) throw new InvalidParamException("Gift consturctor giftMessage is empty");

        this.giftToken = TokenGenerator.randomCharacterWithPrefix(GIFT_PREFIX);
        this.buyerUserId = buyerUserId;
        this.orderToken = orderToken;
        this.status = Status.INIT;
        this.pushType = pushType;
        this.giftReceiverName = giftReceiverName;
        this.giftReceiverPhone = giftReceiverPhone;
        this.giftMessage = giftMessage;
        this.expiredAt = ZonedDateTime.now().plusDays(7);
    }

    public void inPayment() {
        if (this.status != Status.INIT) throw new IllegalStatusException("Gift inPayment");
        this.status = Status.IN_PAYMENT;
    }

    public void completePayment() {
        if (this.status != Status.IN_PAYMENT) throw new IllegalStatusException("Gift paymentComplete");
        this.status = Status.ORDER_COMPLETE;
        this.paidAt = ZonedDateTime.now();
    }

    public void pushLink() {
        if (this.status != Status.ORDER_COMPLETE) throw new IllegalStatusException("Gift pushLink");
        this.status = Status.PUSH_COMPLETE;
        this.pushedAt = ZonedDateTime.now();
    }

    public void accept(GiftCommand.Accept request) {

    }
}
