/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionDetailsUpdateComponent } from 'app/entities/requisition-details/requisition-details-update.component';
import { RequisitionDetailsService } from 'app/entities/requisition-details/requisition-details.service';
import { RequisitionDetails } from 'app/shared/model/requisition-details.model';

describe('Component Tests', () => {
    describe('RequisitionDetails Management Update Component', () => {
        let comp: RequisitionDetailsUpdateComponent;
        let fixture: ComponentFixture<RequisitionDetailsUpdateComponent>;
        let service: RequisitionDetailsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionDetailsUpdateComponent]
            })
                .overrideTemplate(RequisitionDetailsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RequisitionDetailsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequisitionDetailsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RequisitionDetails(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.requisitionDetails = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RequisitionDetails();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.requisitionDetails = entity;
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
