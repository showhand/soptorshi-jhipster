/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPoStatusUpdateComponent } from 'app/entities/commercial-po-status/commercial-po-status-update.component';
import { CommercialPoStatusService } from 'app/entities/commercial-po-status/commercial-po-status.service';
import { CommercialPoStatus } from 'app/shared/model/commercial-po-status.model';

describe('Component Tests', () => {
    describe('CommercialPoStatus Management Update Component', () => {
        let comp: CommercialPoStatusUpdateComponent;
        let fixture: ComponentFixture<CommercialPoStatusUpdateComponent>;
        let service: CommercialPoStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPoStatusUpdateComponent]
            })
                .overrideTemplate(CommercialPoStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialPoStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPoStatusService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPoStatus(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPoStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPoStatus();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPoStatus = entity;
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
