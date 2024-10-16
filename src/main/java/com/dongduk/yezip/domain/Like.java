package com.dongduk.yezip.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name="tb_like")
@Getter
@Setter
@IdClass(LikeId.class) // Like 클래스를 복합 키 클래스로 설정
public class Like implements Serializable {
    @Id
    private int itemId; // Item의 기본 키

    @Id
    private int uid; // User의 기본 키

    @MapsId("uid") // uid가 User의 uid를 참조
    @OneToOne // User와 1:1 관계
    @JoinColumn(name = "uid", referencedColumnName = "uid", insertable = false, updatable = false)
    private User user;

    @MapsId("itemId") // itemId가 Item의 itemId를 참조
    @OneToOne // Item과 1:1 관계
    @JoinColumn(name="itemId", referencedColumnName = "itemId", insertable = false, updatable = false)
    private Item item;
    
}
