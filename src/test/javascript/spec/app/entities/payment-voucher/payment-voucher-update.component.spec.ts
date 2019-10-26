/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PaymentVoucherUpdateComponent } from 'app/entities/payment-voucher/payment-voucher-update.component';
import { PaymentVoucherService } from 'app/entities/payment-voucher/payment-voucher.service';
import { PaymentVoucher } from 'app/shared/model/payment-voucher.model';

describe('Component Tests', () => {
    describe('PaymentVoucher Management Update Component', () => {
        let comp: PaymentVoucherUpdateComponent;
        let fixture: ComponentFixture<PaymentVoucherUpdateComponent>;
        let service: PaymentVoucherService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PaymentVoucherUpdateComponent]
            })
                .overrideTemplate(PaymentVoucherUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PaymentVoucherUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentVoucherService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PaymentVoucher(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.paymentVoucher = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PaymentVoucher();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.paymentVoucher = entity;
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
