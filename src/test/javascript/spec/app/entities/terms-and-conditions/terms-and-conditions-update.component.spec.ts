/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { TermsAndConditionsUpdateComponent } from 'app/entities/terms-and-conditions/terms-and-conditions-update.component';
import { TermsAndConditionsService } from 'app/entities/terms-and-conditions/terms-and-conditions.service';
import { TermsAndConditions } from 'app/shared/model/terms-and-conditions.model';

describe('Component Tests', () => {
    describe('TermsAndConditions Management Update Component', () => {
        let comp: TermsAndConditionsUpdateComponent;
        let fixture: ComponentFixture<TermsAndConditionsUpdateComponent>;
        let service: TermsAndConditionsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [TermsAndConditionsUpdateComponent]
            })
                .overrideTemplate(TermsAndConditionsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TermsAndConditionsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TermsAndConditionsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TermsAndConditions(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.termsAndConditions = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TermsAndConditions();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.termsAndConditions = entity;
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
