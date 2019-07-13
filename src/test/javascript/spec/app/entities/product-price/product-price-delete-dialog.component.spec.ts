/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { ProductPriceDeleteDialogComponent } from 'app/entities/product-price/product-price-delete-dialog.component';
import { ProductPriceService } from 'app/entities/product-price/product-price.service';

describe('Component Tests', () => {
    describe('ProductPrice Management Delete Component', () => {
        let comp: ProductPriceDeleteDialogComponent;
        let fixture: ComponentFixture<ProductPriceDeleteDialogComponent>;
        let service: ProductPriceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProductPriceDeleteDialogComponent]
            })
                .overrideTemplate(ProductPriceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductPriceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductPriceService);
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
