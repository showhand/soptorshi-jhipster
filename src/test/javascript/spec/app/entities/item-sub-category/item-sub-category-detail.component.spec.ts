/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ItemSubCategoryDetailComponent } from 'app/entities/item-sub-category/item-sub-category-detail.component';
import { ItemSubCategory } from 'app/shared/model/item-sub-category.model';

describe('Component Tests', () => {
    describe('ItemSubCategory Management Detail Component', () => {
        let comp: ItemSubCategoryDetailComponent;
        let fixture: ComponentFixture<ItemSubCategoryDetailComponent>;
        const route = ({ data: of({ itemSubCategory: new ItemSubCategory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ItemSubCategoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemSubCategoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemSubCategoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.itemSubCategory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
