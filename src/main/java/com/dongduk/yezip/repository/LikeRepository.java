package com.dongduk.yezip.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dongduk.yezip.domain.Like;
import com.dongduk.yezip.domain.Item;

public interface LikeRepository extends CrudRepository<Like, Integer> {
    
    // 좋아요 누름 
    int createByUidAndItemId(int uid, int itemId);
    
    // 좋아요 취소 
    int deleteByUidAndItemId(int uid, int itemId);
    
    // 좋아요 보관함
    List<Item> getItemListByUid(int uid);
}
