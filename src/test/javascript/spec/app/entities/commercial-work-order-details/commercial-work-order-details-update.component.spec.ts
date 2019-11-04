/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialWorkOrderDetailsUpdateComponent } from 'app/entities/commercial-work-order-details/commercial-work-order-details-update.component';
import { CommercialWorkOrderDetailsService } from 'app/entities/commercial-work-order-details/commercial-work-order-details.service';
import { CommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';

describe('Component Tests', () => {
    describe('CommercialWorkOrderDetails Management Update Component', () => {
        let comp: CommercialWorkOrderDetailsUpdateComponent;
        let fixture: ComponentFixture<CommercialWorkOrderDetailsUpdateComponent>;
        let service: CommercialWorkOrderDetailsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialWorkOrderDetailsUpdateComponent]
            })
                .overrideTemplate(CommercialWorkOrderDetailsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialWorkOrderDetailsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialWorkOrderDetailsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialWorkOrderDetails(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialWorkOrderDetails = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialWorkOrderDetails();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialWorkOrderDetails = entity;
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
