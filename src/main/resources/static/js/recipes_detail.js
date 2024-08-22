function toggleBookmark(recipeId) {
    var bookmarkIcon = document.getElementById('bookmark-icon');
    var currentSrc = bookmarkIcon.src;

    // URL 경로 설정
    var bookmarkUrl = currentSrc.includes('bookmark.png') ? '/bookmark/add' : '/bookmark/remove';

    // AJAX 요청
    fetch(bookmarkUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ recipeId: recipeId }),
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // 아이콘 상태 변경
                var bookmarkSrc = currentSrc.replace('bookmark_filled.png', 'bookmark.png');
                var bookmarkFilledSrc = currentSrc.replace('bookmark.png', 'bookmark_filled.png');

                if (currentSrc.includes('bookmark.png')) {
                    bookmarkIcon.src = bookmarkFilledSrc;
                } else {
                    bookmarkIcon.src = bookmarkSrc;
                }
                alert('북마크를 설정하였습니다.')
            } else {
                alert('북마크 처리에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function toggleDetails(id) {
    var content = document.getElementById(id);
    var icon = document.getElementById(id + '-icon');

    // 현재 아이콘의 경로 가져오기
    var currentSrc = icon.src;

    // 경로에서 파일 이름만 교체
    var upIconSrc = currentSrc.replace('Chevrondown.png', 'Chevronup.png');
    var downIconSrc = currentSrc.replace('Chevronup.png', 'Chevrondown.png');

    // 토글 동작 수행
    if (content.style.display === "none" || content.style.display === "") {
        content.style.display = "block";
        icon.src = upIconSrc;
    } else {
        content.style.display = "none";
        icon.src = downIconSrc;
    }
}


