package com.example.bab_recipes.Controller;

import com.example.bab_recipes.Domain.Bookmark;
import com.example.bab_recipes.Domain.User;
import com.example.bab_recipes.Service.BookMarkService;
import com.example.bab_recipes.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BookmarkController {

    @Autowired
    private BookMarkService bookMarkService;
    @Autowired
    private UserService userService;

    @PostMapping("/addBookmark")
    public ResponseEntity<Map<String, Object>> addBookmark(@RequestBody Bookmark bookmark, HttpSession session) {
        String recipeId = bookmark.getRecipeId();
        int isBookMark = bookmark.getIsBookmark();
        User user = (User) session.getAttribute("user");
        System.out.println("isBookMark = " + isBookMark);
        System.out.println("recipeId = " + recipeId);
        Bookmark newMark = new Bookmark(user, recipeId, isBookMark);

        Map<String, Object> response = new HashMap<>();  // 변경: Map<String, String> -> Map<String, Object>

        try {
            Bookmark savedBookmark = bookMarkService.addBookmark(newMark);
            if (savedBookmark != null) {
                response.put("success", true);  // success 필드 추가
                response.put("message", "북마크 추가 완료");
                System.out.println("북마크추가완료");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);  // success 필드 추가
                response.put("message", "북마크 추가 중 오류 발생");
                System.out.println("북마크 추가 실패");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (DataIntegrityViolationException e) {
            // 중복 데이터 예외 처리
            response.put("success", false);  // success 필드 추가
            response.put("message", "이미 존재하는 북마크입니다.");
            System.out.println("북마크 중복");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            // 예외 발생 시 로그 기록
            e.printStackTrace();
            System.out.println("통신오류");
            System.out.println(e.getMessage());
            response.put("success", false);  // success 필드 추가
            response.put("message", "서버 오류 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/removeBookmark")
    public ResponseEntity<Map<String,Object>> removeBookmark(@RequestBody Bookmark bookmark) {
        String recipeId = bookmark.getRecipeId();
        int isBookmark = bookmark.getIsBookmark();

        Map<String, Object> response = new HashMap<>();

        try {
            int result = bookMarkService.DeleteBookmark(recipeId);
            if (result > 0) {
                response.put("success", true);  // success 필드 추가
                response.put("message", "북마크제거완료");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);  // success 필드 추가
                response.put("message", "서버 오류발생");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);  // success 필드 추가
            response.put("message", "Exception 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
}
