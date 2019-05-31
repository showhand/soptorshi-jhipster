/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { PayrollManagementComponent } from 'app/entities/payroll-management/payroll-management.component';
import { PayrollManagementService } from 'app/entities/payroll-management/payroll-management.service';
import { PayrollManagement } from 'app/shared/model/payroll-management.model';

describe('Component Tests', () => {
    describe('PayrollManagement Management Component', () => {
        let comp: PayrollManagementComponent;
        let fixture: ComponentFixture<PayrollManagementComponent>;
        let service: PayrollManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PayrollManagementComponent],
                providers: []
            })
                .overrideTemplate(PayrollManagementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PayrollManagementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollManagementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PayrollManagement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.payrollManagements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
