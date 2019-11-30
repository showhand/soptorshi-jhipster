/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SalaryVoucherRelationDetailComponent } from 'app/entities/salary-voucher-relation/salary-voucher-relation-detail.component';
import { SalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';

describe('Component Tests', () => {
    describe('SalaryVoucherRelation Management Detail Component', () => {
        let comp: SalaryVoucherRelationDetailComponent;
        let fixture: ComponentFixture<SalaryVoucherRelationDetailComponent>;
        const route = ({ data: of({ salaryVoucherRelation: new SalaryVoucherRelation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SalaryVoucherRelationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SalaryVoucherRelationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalaryVoucherRelationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.salaryVoucherRelation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
