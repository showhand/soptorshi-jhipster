/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPackagingDetailsUpdateComponent } from 'app/entities/commercial-packaging-details/commercial-packaging-details-update.component';
import { CommercialPackagingDetailsService } from 'app/entities/commercial-packaging-details/commercial-packaging-details.service';
import { CommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';

describe('Component Tests', () => {
    describe('CommercialPackagingDetails Management Update Component', () => {
        let comp: CommercialPackagingDetailsUpdateComponent;
        let fixture: ComponentFixture<CommercialPackagingDetailsUpdateComponent>;
        let service: CommercialPackagingDetailsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPackagingDetailsUpdateComponent]
            })
                .overrideTemplate(CommercialPackagingDetailsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialPackagingDetailsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPackagingDetailsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPackagingDetails(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPackagingDetails = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPackagingDetails();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPackagingDetails = entity;
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
