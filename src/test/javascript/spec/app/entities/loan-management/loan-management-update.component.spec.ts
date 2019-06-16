/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { LoanManagementUpdateComponent } from 'app/entities/loan-management/loan-management-update.component';
import { LoanManagementService } from 'app/entities/loan-management/loan-management.service';
import { LoanManagement } from 'app/shared/model/loan-management.model';

describe('Component Tests', () => {
    describe('LoanManagement Management Update Component', () => {
        let comp: LoanManagementUpdateComponent;
        let fixture: ComponentFixture<LoanManagementUpdateComponent>;
        let service: LoanManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [LoanManagementUpdateComponent]
            })
                .overrideTemplate(LoanManagementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoanManagementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoanManagementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LoanManagement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.loanManagement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LoanManagement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.loanManagement = entity;
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
