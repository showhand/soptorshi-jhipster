/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { VoucherNumberControlDetailComponent } from 'app/entities/voucher-number-control/voucher-number-control-detail.component';
import { VoucherNumberControl } from 'app/shared/model/voucher-number-control.model';

describe('Component Tests', () => {
    describe('VoucherNumberControl Management Detail Component', () => {
        let comp: VoucherNumberControlDetailComponent;
        let fixture: ComponentFixture<VoucherNumberControlDetailComponent>;
        const route = ({ data: of({ voucherNumberControl: new VoucherNumberControl(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [VoucherNumberControlDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VoucherNumberControlDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VoucherNumberControlDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.voucherNumberControl).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
