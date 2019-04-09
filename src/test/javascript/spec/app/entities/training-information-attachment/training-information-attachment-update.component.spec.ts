/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { TrainingInformationAttachmentUpdateComponent } from 'app/entities/training-information-attachment/training-information-attachment-update.component';
import { TrainingInformationAttachmentService } from 'app/entities/training-information-attachment/training-information-attachment.service';
import { TrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';

describe('Component Tests', () => {
    describe('TrainingInformationAttachment Management Update Component', () => {
        let comp: TrainingInformationAttachmentUpdateComponent;
        let fixture: ComponentFixture<TrainingInformationAttachmentUpdateComponent>;
        let service: TrainingInformationAttachmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [TrainingInformationAttachmentUpdateComponent]
            })
                .overrideTemplate(TrainingInformationAttachmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TrainingInformationAttachmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrainingInformationAttachmentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TrainingInformationAttachment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.trainingInformationAttachment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TrainingInformationAttachment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.trainingInformationAttachment = entity;
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
