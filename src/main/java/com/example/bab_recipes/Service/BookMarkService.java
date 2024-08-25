package com.example.bab_recipes.Service;

import com.example.bab_recipes.Domain.Bookmark;
import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Repository.BookmarkRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookMarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    //단일 북마크설정여부
    public Optional<Bookmark> statusMark(String id) {
        return bookmarkRepository.findByRecipeId(id);
    }

    //리스트 북마크 설정여부
    public List<Bookmark> getAllBookmarks(List<String> recipeIds) {
        return bookmarkRepository.findBookmarksByRecipeIds(recipeIds);
    }

    public Bookmark addBookmark(Bookmark bookmark) {
        String recipeId = bookmark.getRecipeId();
        Optional<Bookmark> byRecipeId = bookmarkRepository.findByRecipeId(recipeId);
        if (byRecipeId.isPresent()) {
            return bookmark;
        }else{
            return bookmarkRepository.save(bookmark);
        }

    }

    @Transactional
    public int DeleteBookmark(String recipeId) {
        return bookmarkRepository.deleteByRecipeId(recipeId);
    }
}
