package com.shay.aipets.entity.result;

import com.shay.aipets.entity.UserCommentItem;

import java.util.List;

public class GetUserCommentResult {
    String errorMsg = "";
    List<UserCommentItem> userCommentItemList;
    boolean hasMore;
    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<UserCommentItem> getUserCommentItemList() {
        return userCommentItemList;
    }

    public void setUserCommentItemList(List<UserCommentItem> userCommentItemList) {
        this.userCommentItemList = userCommentItemList;
    }
}
