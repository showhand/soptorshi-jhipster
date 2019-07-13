/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionUpdateComponent } from 'app/entities/requisition/requisition-update.component';
import { RequisitionService } from 'app/entities/requisition/requisition.service';
import { Requisition } from 'app/shared/model/requisition.model';

describe('Component Tests', () => {
    describe('Requisition Management Update Component', () => {
        let comp: RequisitionUpdateComponent;
        let fixture: ComponentFixture<RequisitionUpdateComponent>;
        let service: RequisitionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionUpdateComponent]
            })
                .overrideTemplate(RequisitionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RequisitionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequisitionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Requisition(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.requisition = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Requisition();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.requisition = entity;
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
