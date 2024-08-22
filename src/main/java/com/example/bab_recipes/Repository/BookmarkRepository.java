package com.example.bab_recipes.Repository;

import com.example.bab_recipes.Domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
