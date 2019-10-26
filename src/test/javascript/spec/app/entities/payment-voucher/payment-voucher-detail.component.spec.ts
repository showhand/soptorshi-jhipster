/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PaymentVoucherDetailComponent } from 'app/entities/payment-voucher/payment-voucher-detail.component';
import { PaymentVoucher } from 'app/shared/model/payment-voucher.model';

describe('Component Tests', () => {
    describe('PaymentVoucher Management Detail Component', () => {
        let comp: PaymentVoucherDetailComponent;
        let fixture: ComponentFixture<PaymentVoucherDetailComponent>;
        const route = ({ data: of({ paymentVoucher: new PaymentVoucher(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PaymentVoucherDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PaymentVoucherDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaymentVoucherDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.paymentVoucher).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
