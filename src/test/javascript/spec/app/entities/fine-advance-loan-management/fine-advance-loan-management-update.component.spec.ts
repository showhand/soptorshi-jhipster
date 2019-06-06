/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { FineAdvanceLoanManagementUpdateComponent } from 'app/entities/fine-advance-loan-management/fine-advance-loan-management-update.component';
import { FineAdvanceLoanManagementService } from 'app/entities/fine-advance-loan-management/fine-advance-loan-management.service';
import { FineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';

describe('Component Tests', () => {
    describe('FineAdvanceLoanManagement Management Update Component', () => {
        let comp: FineAdvanceLoanManagementUpdateComponent;
        let fixture: ComponentFixture<FineAdvanceLoanManagementUpdateComponent>;
        let service: FineAdvanceLoanManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FineAdvanceLoanManagementUpdateComponent]
            })
                .overrideTemplate(FineAdvanceLoanManagementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FineAdvanceLoanManagementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FineAdvanceLoanManagementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FineAdvanceLoanManagement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fineAdvanceLoanManagement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FineAdvanceLoanManagement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fineAdvanceLoanManagement = entity;
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
