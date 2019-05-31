/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PayrollManagementUpdateComponent } from 'app/entities/payroll-management/payroll-management-update.component';
import { PayrollManagementService } from 'app/entities/payroll-management/payroll-management.service';
import { PayrollManagement } from 'app/shared/model/payroll-management.model';

describe('Component Tests', () => {
    describe('PayrollManagement Management Update Component', () => {
        let comp: PayrollManagementUpdateComponent;
        let fixture: ComponentFixture<PayrollManagementUpdateComponent>;
        let service: PayrollManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PayrollManagementUpdateComponent]
            })
                .overrideTemplate(PayrollManagementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PayrollManagementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollManagementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PayrollManagement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.payrollManagement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PayrollManagement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.payrollManagement = entity;
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
