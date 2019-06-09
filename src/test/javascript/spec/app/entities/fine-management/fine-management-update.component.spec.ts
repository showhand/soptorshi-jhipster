/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { FineManagementUpdateComponent } from 'app/entities/fine-management/fine-management-update.component';
import { FineManagementService } from 'app/entities/fine-management/fine-management.service';
import { FineManagement } from 'app/shared/model/fine-management.model';

describe('Component Tests', () => {
    describe('FineManagement Management Update Component', () => {
        let comp: FineManagementUpdateComponent;
        let fixture: ComponentFixture<FineManagementUpdateComponent>;
        let service: FineManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FineManagementUpdateComponent]
            })
                .overrideTemplate(FineManagementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FineManagementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FineManagementService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FineManagement(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fineManagement = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FineManagement();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fineManagement = entity;
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
