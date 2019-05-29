/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PayrollManagementDetailComponent } from 'app/entities/payroll-management/payroll-management-detail.component';
import { PayrollManagement } from 'app/shared/model/payroll-management.model';

describe('Component Tests', () => {
    describe('PayrollManagement Management Detail Component', () => {
        let comp: PayrollManagementDetailComponent;
        let fixture: ComponentFixture<PayrollManagementDetailComponent>;
        const route = ({ data: of({ payrollManagement: new PayrollManagement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PayrollManagementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PayrollManagementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PayrollManagementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.payrollManagement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
