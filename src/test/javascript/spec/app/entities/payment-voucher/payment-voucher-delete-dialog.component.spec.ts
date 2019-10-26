/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { PaymentVoucherDeleteDialogComponent } from 'app/entities/payment-voucher/payment-voucher-delete-dialog.component';
import { PaymentVoucherService } from 'app/entities/payment-voucher/payment-voucher.service';

describe('Component Tests', () => {
    describe('PaymentVoucher Management Delete Component', () => {
        let comp: PaymentVoucherDeleteDialogComponent;
        let fixture: ComponentFixture<PaymentVoucherDeleteDialogComponent>;
        let service: PaymentVoucherService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PaymentVoucherDeleteDialogComponent]
            })
                .overrideTemplate(PaymentVoucherDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaymentVoucherDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentVoucherService);
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
