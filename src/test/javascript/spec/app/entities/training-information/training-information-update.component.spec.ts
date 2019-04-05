/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { TrainingInformationUpdateComponent } from 'app/entities/training-information/training-information-update.component';
import { TrainingInformationService } from 'app/entities/training-information/training-information.service';
import { TrainingInformation } from 'app/shared/model/training-information.model';

describe('Component Tests', () => {
    describe('TrainingInformation Management Update Component', () => {
        let comp: TrainingInformationUpdateComponent;
        let fixture: ComponentFixture<TrainingInformationUpdateComponent>;
        let service: TrainingInformationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [TrainingInformationUpdateComponent]
            })
                .overrideTemplate(TrainingInformationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TrainingInformationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingInformationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TrainingInformation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.trainingInformation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TrainingInformation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.trainingInformation = entity;
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
