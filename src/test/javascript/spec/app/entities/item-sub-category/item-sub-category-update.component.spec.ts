/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ItemSubCategoryUpdateComponent } from 'app/entities/item-sub-category/item-sub-category-update.component';
import { ItemSubCategoryService } from 'app/entities/item-sub-category/item-sub-category.service';
import { ItemSubCategory } from 'app/shared/model/item-sub-category.model';

describe('Component Tests', () => {
    describe('ItemSubCategory Management Update Component', () => {
        let comp: ItemSubCategoryUpdateComponent;
        let fixture: ComponentFixture<ItemSubCategoryUpdateComponent>;
        let service: ItemSubCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ItemSubCategoryUpdateComponent]
            })
                .overrideTemplate(ItemSubCategoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ItemSubCategoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemSubCategoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemSubCategory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemSubCategory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemSubCategory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemSubCategory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
