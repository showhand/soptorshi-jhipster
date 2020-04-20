/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { PurchaseOrderVoucherRelationDeleteDialogComponent } from 'app/entities/purchase-order-voucher-relation/purchase-order-voucher-relation-delete-dialog.component';
import { PurchaseOrderVoucherRelationService } from 'app/entities/purchase-order-voucher-relation/purchase-order-voucher-relation.service';

describe('Component Tests', () => {
    describe('PurchaseOrderVoucherRelation Management Delete Component', () => {
        let comp: PurchaseOrderVoucherRelationDeleteDialogComponent;
        let fixture: ComponentFixture<PurchaseOrderVoucherRelationDeleteDialogComponent>;
        let service: PurchaseOrderVoucherRelationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PurchaseOrderVoucherRelationDeleteDialogComponent]
            })
                .overrideTemplate(PurchaseOrderVoucherRelationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseOrderVoucherRelationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseOrderVoucherRelationService);
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
