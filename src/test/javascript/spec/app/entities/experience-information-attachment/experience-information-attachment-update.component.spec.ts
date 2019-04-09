/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ExperienceInformationAttachmentUpdateComponent } from 'app/entities/experience-information-attachment/experience-information-attachment-update.component';
import { ExperienceInformationAttachmentService } from 'app/entities/experience-information-attachment/experience-information-attachment.service';
import { ExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';

describe('Component Tests', () => {
    describe('ExperienceInformationAttachment Management Update Component', () => {
        let comp: ExperienceInformationAttachmentUpdateComponent;
        let fixture: ComponentFixture<ExperienceInformationAttachmentUpdateComponent>;
        let service: ExperienceInformationAttachmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ExperienceInformationAttachmentUpdateComponent]
            })
                .overrideTemplate(ExperienceInformationAttachmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperienceInformationAttachmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceInformationAttachmentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperienceInformationAttachment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experienceInformationAttachment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperienceInformationAttachment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experienceInformationAttachment = entity;
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
