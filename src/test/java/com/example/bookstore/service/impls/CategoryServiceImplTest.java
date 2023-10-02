package com.example.bookstore.service.impls;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.request.CategoryRequestDto;
import com.example.bookstore.dto.response.CategoryResponseDto;
import com.example.bookstore.mapper.CategoryMapper;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper mapper;
    private CategoryRequestDto requestDto;
    private Category category;
    private CategoryResponseDto expected;

    @BeforeEach
    void setUp() {
        requestDto = new CategoryRequestDto();
        requestDto.setName("Test category");
        requestDto.setDescription("Test category description");

        Long categoryId = 1L;
        category = new Category();
        category.setId(categoryId);
        category.setName(requestDto.getName());
        category.setDescription(requestDto.getDescription());

        expected = new CategoryResponseDto();
        expected.setId(category.getId());
        expected.setName(category.getName());
        expected.setDescription(category.getDescription());
    }

    @Test
    @DisplayName("Verify save method works")
    void save_ValidRequestCategoryDto_Success() {
        //given
        PageImpl<Category> categories = new PageImpl<>(List.of(new Category()));
        when(categoryRepository.findAll(Pageable.unpaged()))
                .thenReturn(categories);
        when(categories.get().toList().contains(requestDto.getName())).thenReturn(false);
        when(mapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(mapper.toDto(any(Category.class))).thenReturn(expected);
        //when
        CategoryResponseDto actual = categoryService.save(requestDto);
        //then
        assertThat(actual).isEqualTo(expected);
        verify(categoryRepository, times(1)).save(category);
        verifyNoMoreInteractions(mapper, categoryRepository);
    }

    @Test
    void findAll() {
    }

    @Test
    void getById() {

    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}
