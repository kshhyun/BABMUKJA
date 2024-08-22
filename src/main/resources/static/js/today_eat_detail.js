document.querySelectorAll('.bookmark-icon').forEach(icon => {
    icon.addEventListener('click', function() {
        if (this.src.includes('bookmark.png')) {
            this.src = 'images/bookmark_filled.png';
        } else {
            this.src = 'images/bookmark.png';
        }
    });
});


document.querySelectorAll('.sort-option').forEach(option => {
    option.addEventListener('click', function() {
        document.querySelectorAll('.sort-option').forEach(opt => opt.classList.remove('active'));
        this.classList.add('active');

        // 여기에 별점 순서에 따라 레시피를 정렬하는 코드를 추가할 수 있습니다.
        // sortRecipes(this.id); // 예: sort-high 또는 sort-low에 따라 정렬
    });
});

document.addEventListener('DOMContentLoaded', function (){
    const recipeItems = document.querySelectorAll('.recipe-item');
    console.log("눌렸다")
    recipeItems.forEach(item => {
        item.addEventListener('click', function(){
            const hiddenInput = this.querySelector('input[type="hidden"]');
            const recipeId = hiddenInput.value;

            window.location.href = '/recipe/detail/' + recipeId;
        })
    })

})