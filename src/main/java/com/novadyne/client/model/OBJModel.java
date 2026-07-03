package com.novadyne.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class OBJModel {
    private final float[] vertices;
    private final float[] uvs;
    private final float[] normals;
    private final int[] indices;
    private final int vertexCount;

    public OBJModel(float[] vertices, float[] uvs, float[] normals, int[] indices) {
        this.vertices = vertices;
        this.uvs = uvs;
        this.normals = normals;
        this.indices = indices;
        this.vertexCount = vertices.length / 3;
    }

    public void render(PoseStack.Pose pose, VertexConsumer consumer, int packedLight, int packedOverlay, float r, float g, float b, float a) {
        for (int i = 0; i < indices.length; i += 3) {
            for (int j = 0; j < 3; j++) {
                int idx = indices[i + j];
                consumer.addVertex(pose, vertices[idx * 3], vertices[idx * 3 + 1], vertices[idx * 3 + 2])
                        .setColor(r, g, b, a)
                        .setUv(uvs[idx * 2], uvs[idx * 2 + 1])
                        .setOverlay(packedOverlay)
                        .setLight(packedLight)
                        .setNormal(pose, normals[idx * 3], normals[idx * 3 + 1], normals[idx * 3 + 2]);
            }
        }
    }

    public float[] getVertices() { return vertices; }
    public float[] getUvs() { return uvs; }
    public float[] getNormals() { return normals; }
    public int[] getIndices() { return indices; }
    public int getVertexCount() { return vertexCount; }
    public int getTriangleCount() { return indices.length / 3; }
}
