/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { ItemCategoryDeleteDialogComponent } from 'app/entities/item-category/item-category-delete-dialog.component';
import { ItemCategoryService } from 'app/entities/item-category/item-category.service';

describe('Component Tests', () => {
    describe('ItemCategory Management Delete Component', () => {
        let comp: ItemCategoryDeleteDialogComponent;
        let fixture: ComponentFixture<ItemCategoryDeleteDialogComponent>;
        let service: ItemCategoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ItemCategoryDeleteDialogComponent]
            })
                .overrideTemplate(ItemCategoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemCategoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemCategoryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
