package com.example.bab_recipes.Service;

import com.example.bab_recipes.Domain.Bookmark;
import com.example.bab_recipes.Repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;


    public boolean isBookMark(Long userId) {

        return bookmarkRepository.findById(userId).isPresent();
    }

    public boolean addBookmark(Long userId, String recipeId) {
        Bookmark bookmark = new Bookmark(userId, recipeId);
        bookmarkRepository.save(bookmark);

        return true;
    }

    public boolean removeBookmark(Long userId, String recipeId) {
        return true;
    }
}
