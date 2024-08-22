// category nav
document.addEventListener('DOMContentLoaded', function() {
    const navItems = document.querySelectorAll('.nav-item');
    const contentSections = document.querySelectorAll('.content');

    navItems.forEach(item => {
        item.addEventListener('click', function() {
            navItems.forEach(nav => nav.classList.remove('active'));
            this.classList.add('active');

            contentSections.forEach(section => section.style.display = 'none');
            const target = this.getAttribute('data-target');
            document.getElementById(target).style.display = 'block';
        });
    });

    document.querySelector('.nav-item[data-target="all"]').classList.add('active');
    document.getElementById('all').style.display = 'block';
});

// 로고 타이핑
document.addEventListener("DOMContentLoaded", function() {
    const text = "| R E C I P E S |";
    const textElement = document.getElementById("recipes-title"); // 여기부분 id수정
    let index = 0;
    const typingSpeed = 150; // 타이핑 속도 (밀리초)
    const pauseDuration = 30000; // 30초 간격 (밀리초)

    function typeText() {
        if (index < text.length) {
            textElement.innerHTML += text.charAt(index);
            index++;
            setTimeout(typeText, typingSpeed);
        } else {
            setTimeout(() => {
                textElement.innerHTML = ''; // 텍스트 초기화
                index = 0; // 인덱스 초기화
                typeText(); // 타이핑 다시 시작
            }, pauseDuration);
        }
    }

    typeText(); // 첫 타이핑 시작
});
