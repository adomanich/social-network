package com.example.demo.listener;

import com.example.demo.model.UserModel;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class UserModelListener extends AbstractMongoEventListener<UserModel> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<UserModel> event) {
        event.getSource().setId(System.currentTimeMillis());

    }
}
