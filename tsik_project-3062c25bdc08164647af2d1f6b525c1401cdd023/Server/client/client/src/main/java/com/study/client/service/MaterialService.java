package com.study.client.service;

import java.util.Collections;
import java.util.List;

public class MaterialService {

    public List<String> getMaterials(Long groupId) throws Exception {
        // Пока сервер не поддерживает материалы —
        // возвращаем пустой список, чтобы UI не падал
        return Collections.emptyList();
    }

    public void addMaterial(Long groupId, String title) throws Exception {
        // TODO: реализовать, когда сервер добавит API для материалов
    }
}