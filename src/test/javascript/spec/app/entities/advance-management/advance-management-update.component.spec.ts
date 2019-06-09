/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AdvanceManagementUpdateComponent } from 'app/entities/advance-management/advance-management-update.component';
import { AdvanceManagementService } from 'app/entities/advance-management/advance-management.service';
import { AdvanceManagement } from 'app/shared/model/advance-management.model';

describe('Component Tests', () => {
    describe('AdvanceManagement Management Update Component', () => {
        let comp: AdvanceManagementUpdateComponent;
        let fixture: ComponentFixture<AdvanceManagementUpdateComponent>;
        let service: AdvanceManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AdvanceManagementUpdateComponent]
            })
                .overrideTemplate(AdvanceManagementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdvanceManagementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdvanceManagementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AdvanceManagement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.advanceManagement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AdvanceManagement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.advanceManagement = entity;
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
