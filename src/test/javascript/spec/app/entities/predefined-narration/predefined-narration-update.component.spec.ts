/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PredefinedNarrationUpdateComponent } from 'app/entities/predefined-narration/predefined-narration-update.component';
import { PredefinedNarrationService } from 'app/entities/predefined-narration/predefined-narration.service';
import { PredefinedNarration } from 'app/shared/model/predefined-narration.model';

describe('Component Tests', () => {
    describe('PredefinedNarration Management Update Component', () => {
        let comp: PredefinedNarrationUpdateComponent;
        let fixture: ComponentFixture<PredefinedNarrationUpdateComponent>;
        let service: PredefinedNarrationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PredefinedNarrationUpdateComponent]
            })
                .overrideTemplate(PredefinedNarrationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PredefinedNarrationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PredefinedNarrationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PredefinedNarration(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.predefinedNarration = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PredefinedNarration();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.predefinedNarration = entity;
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
